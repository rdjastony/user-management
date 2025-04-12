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
