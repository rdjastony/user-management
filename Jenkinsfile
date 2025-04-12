pipeline {
    agent any

    environment {
        API_IMAGE = 'api-service'
        USER_IMAGE = 'user-service'
        FRONTEND_IMAGE = 'frontend'
        REGISTRY = 'abhishek7840'
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
                    def COMMIT_HASH = sh(script: 'git rev-parse --short HEAD', returnStdout: true).trim()
                    def VERSION = "v${BUILD_NUMBER}-${COMMIT_HASH}"
                    env.VERSION_TAG = VERSION
                }
            }
        } // ✅ This closing brace was missing

        stage('Build API Jar') {
            steps {
                dir('api-service/api') {
                    sh 'mvn clean package -DskipTests'
                }
            }
        }

        stage('Build API Service') {
            steps {
                script {
                    sh "docker build -t ${REGISTRY}/${API_IMAGE}:${env.VERSION_TAG} ./api-service/api"
                }
            }
        }


        stage('Build API Service') {
            steps {
                script {
                    sh "docker build -t ${REGISTRY}/${API_IMAGE}:${env.VERSION_TAG} ./api-service/api"
                }
            }
        }

        stage('Build User Service') {
            steps {
                script {
                    sh "docker build -t ${REGISTRY}/${USER_IMAGE}:${env.VERSION_TAG} ./user-service/user"
                }
            }
        }

        stage('Build Frontend Service') {
            steps {
                script {
                    sh "docker build -t ${REGISTRY}/${FRONTEND_IMAGE}:${env.VERSION_TAG} ./frontend"
                }
            }
        }

        stage('Push Docker Images') {
            steps {
                script {
                    sh "docker push ${REGISTRY}/${API_IMAGE}:${env.VERSION_TAG}"
                    sh "docker push ${REGISTRY}/${USER_IMAGE}:${env.VERSION_TAG}"
                    sh "docker push ${REGISTRY}/${FRONTEND_IMAGE}:${env.VERSION_TAG}"
                }
            }
        }

        stage('Deploy Services') {
            steps {
                script {
                    sh "VERSION_TAG=${env.VERSION_TAG} docker-compose up -d"
                }
            }
        }

        stage('Clean Up') {
            steps {
                script {
                    sh 'docker system prune -f'
                }
            }
        }
    }

    post {
        always {
            echo 'Cleaning up Docker resources.'
            script {
                sh 'docker system prune -f'
            }
        }
    }
}
