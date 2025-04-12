pipeline {
    agent any

    environment {
        API_IMAGE = 'api-service'
        USER_IMAGE = 'user-service'
        FRONTEND_IMAGE = 'frontend'
        REGISTRY = 'abhishek7840'
        COMMIT_HASH = ''
        VERSION_TAG = ''
    }

    stages {
        stage('Clone Repo') {
            steps {
                git branch: 'main', url: 'https://github.com/rdjastony/user-management'
            }
        }

        stage('Set Build Info') {
            steps {
                script {
                    env.COMMIT_HASH = sh(script: 'git rev-parse --short HEAD', returnStdout: true).trim()
                    env.VERSION_TAG = "v${BUILD_NUMBER}-${env.COMMIT_HASH}"
                }
            }
        }

        stage('Build API Service') {
            steps {
                sh "docker build -t ${REGISTRY}/${API_IMAGE}:${VERSION_TAG} ./api-service"
            }
        }

        stage('Build User Service') {
            steps {
                sh "docker build -t ${REGISTRY}/${USER_IMAGE}:${VERSION_TAG} ./user-service"
            }
        }

        stage('Build Frontend Service') {
            steps {
                sh "docker build -t ${REGISTRY}/${FRONTEND_IMAGE}:${VERSION_TAG} ./frontend"
            }
        }

        stage('Push Docker Images') {
            steps {
                sh "docker push ${REGISTRY}/${API_IMAGE}:${VERSION_TAG}"
                sh "docker push ${REGISTRY}/${USER_IMAGE}:${VERSION_TAG}"
                sh "docker push ${REGISTRY}/${FRONTEND_IMAGE}:${VERSION_TAG}"
            }
        }

        stage('Deploy Services') {
            steps {
                sh "VERSION_TAG=${VERSION_TAG} docker-compose up -d"
            }
        }

        stage('Clean Up') {
            steps {
                sh 'docker system prune -f'
            }
        }
    }

    post {
        always {
            echo 'Cleaning up Docker resources.'
            sh 'docker system prune -f'
        }
    }
}
