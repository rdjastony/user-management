pipeline {
    agent any

    environment {
        IMAGE_NAME = 'user-service'
        DOCKER_REGISTRY = 'your-dockerhub-username'  // Replace with actual username
    }

    stages {
        stage('Checkout') {
            steps {
                git 'https://your-repo-url.git' // Replace with your actual Git repo URL
            }
        }

        stage('Build') {
            steps {
                sh './mvnw clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t $DOCKER_REGISTRY/$IMAGE_NAME:latest .'
            }
        }

        stage('Push to Docker Hub') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhub-creds', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    sh 'echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin'
                    sh 'docker push $DOCKER_REGISTRY/$IMAGE_NAME:latest'
                }
            }
        }

        stage('Deploy to Minikube') {
            steps {
                sh 'kubectl apply -f k8s/deployment.yaml'
                sh 'kubectl apply -f k8s/service.yaml'
            }
        }
    }

    post {
        success {
            echo '✅ Successfully Deployed!'
        }
        failure {
            echo '❌ Deployment Failed!'
        }
    }
}
