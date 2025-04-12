pipeline {
    agent any

    environment {
        API_IMAGE = 'api-service'
        USER_IMAGE = 'user-service'
        FRONTEND_IMAGE = 'frontend'
        REGISTRY = 'abhishek7840'  // Your DockerHub repository (no need for "your-repo")
    }

    stages {
        // Step 1: Clone Repository
        stage('Clone Repo') {
            steps {
                git 'https://github.com/rdjastony/user-management' // Change to your repo URL
            }
        }

        // Step 2: Build API Service
        stage('Build API Service') {
            steps {
                script {
                    def commitHash = sh(script: 'git rev-parse --short HEAD', returnStdout: true).trim()
                    def versionTag = "v${BUILD_NUMBER}-${commitHash}"
                    sh "docker build -t ${REGISTRY}/${API_IMAGE}:${versionTag} ./api-service"
                }
            }
        }

        // Step 3: Build User Service
        stage('Build User Service') {
            steps {
                script {
                    def commitHash = sh(script: 'git rev-parse --short HEAD', returnStdout: true).trim()
                    def versionTag = "v${BUILD_NUMBER}-${commitHash}"
                    sh "docker build -t ${REGISTRY}/${USER_IMAGE}:${versionTag} ./user-service"
                }
            }
        }

        // Step 4: Build Frontend Service
        stage('Build Frontend Service') {
            steps {
                script {
                    def commitHash = sh(script: 'git rev-parse --short HEAD', returnStdout: true).trim()
                    def versionTag = "v${BUILD_NUMBER}-${commitHash}"
                    sh "docker build -t ${REGISTRY}/${FRONTEND_IMAGE}:${versionTag} ./frontend"
                }
            }
        }

        // Step 5: Push Docker Images to Registry
        stage('Push Docker Images') {
            steps {
                script {
                    def versionTag = "v${BUILD_NUMBER}-${commitHash}"
                    sh "docker push ${REGISTRY}/${API_IMAGE}:${versionTag}"
                    sh "docker push ${REGISTRY}/${USER_IMAGE}:${versionTag}"
                    sh "docker push ${REGISTRY}/${FRONTEND_IMAGE}:${versionTag}"
                }
            }
        }

        // Step 6: Deploy Services
        stage('Deploy Services') {
            steps {
                script {
                    // Pass versionTag as an environment variable to docker-compose
                    def versionTag = "v${BUILD_NUMBER}-${commitHash}"
                    sh "VERSION_TAG=${versionTag} docker-compose up -d"  // Run docker-compose with the version tag
                }
            }
        }

        // Step 7: Clean Up
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
            sh 'docker system prune -f'
        }
    }
}
