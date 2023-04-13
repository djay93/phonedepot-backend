pipeline {
    agent any
    tools {
        maven "Maven 3.6.3"
    }
    stages {
        stage('SCM'){
            steps{
                git 'https://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@github.com/goodtester/phonedepot-backend.git'
            }
        }
        stage('Build') {
            steps {
                sh "mvn clean package"
            }
        }
        stage('Docker Image') {
            steps{
                sh "docker build --platform=linux/amd64 -t goodtester/phonedepot-backend:${BUILD_NUMBER} ."
                sh "docker tag goodtester/phonedepot-backend:${BUILD_NUMBER} 086166736896.dkr.ecr.us-east-1.amazonaws.com/phonedepot-backend:${BUILD_NUMBER}"
            }
        }
        stage('Docker Push') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'docker_credentials', passwordVariable: 'password', usernameVariable: 'username')]) {
                    sh "aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin 086166736896.dkr.ecr.us-east-1.amazonaws.com"
                }
                sh "docker push 086166736896.dkr.ecr.us-east-1.amazonaws.com/phonedepot-backend:${BUILD_NUMBER}"
            }
        }
        stage("Deploy") {
            steps{
                sshagent (credentials: ['aws-ssh-key']) {
                    withCredentials([usernamePassword(credentialsId: 'docker_credentials', passwordVariable: 'password', usernameVariable: 'username')]) {
                        sh "ssh ${EC2_IP} docker login -u ${username} -p ${password}"
                    }
                    sh 'ssh ${EC2_IP}  docker stop phonedepot-backend || echo "no containers to stop"'
                    sh 'ssh ${EC2_IP}  docker rm phonedepot-backend || echo "no containers to remove"'
                    sh 'ssh ${EC2_IP}  docker rmi goodtester/phonedepot-backend:${BUILD_NUMBER} || echo "no images to delete"'
                    sh 'ssh ${EC2_IP}  docker pull 086166736896.dkr.ecr.us-east-1.amazonaws.com/phonedepot-backend:${BUILD_NUMBER}'
                    sh 'ssh ${EC2_IP}  docker run -d --name phonedepot-backend -p 8080:8080 goodtester/phonedepot-backend:${BUILD_NUMBER}'
                }
            }
        }
        stage('Email Build Status'){
            steps{
                emailext body: "${currentBuild.currentResult}: Job ${env.JOB_NAME} build ${env.BUILD_NUMBER} More info at: ${env.BUILD_URL}", recipientProviders: [[$class: 'DevelopersRecipientProvider'], [$class: 'RequesterRecipientProvider']], subject: "Jenkins Build ${currentBuild.currentResult}: Job ${env.JOB_NAME}"
            }
        }
    }
}