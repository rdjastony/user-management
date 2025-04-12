pipeline {
    agent any

    tools {
        maven 'Maven 3'
        jdk 'JDK 17'
    }

    environment {
        JAVA_HOME = "${tool 'JDK 17'}"
        PATH = "${JAVA_HOME}/bin:${env.PATH}"
    }

    stages {
        // Checkout the source code
        stage('Checkout') {
            steps {
                git url: 'https://github.com/rdjastony/user-management', branch: 'main'
            }
        }

        // Build the user-service project with Maven
        stage('Build user-service with Maven') {
            steps {
                dir('user-service/user') {  // Specify the relative path to the user-service directory
                    sh 'mvn clean install'
                }
            }
        }

        // Build the api-service project with Maven
        stage('Build api-service with Maven') {
            steps {
                dir('api-service/api') {  // Specify the relative path to the api-service directory
                    sh 'mvn clean install'
                }
            }
        }

        // Test the user-service application
        stage('Test user-service') {
            steps {
                dir('user-service/user') {
                    sh 'mvn test'
                }
            }
            post {
                success {
                    echo 'user-service tests passed successfully.'
                }
                failure {
                    echo 'user-service tests failed. Check logs for details.'
                }
            }
        }

        // Test the api-service application
        stage('Test api-service') {
            steps {
                dir('api-service/api') {
                    sh 'mvn test'
                }
            }
            post {
                success {
                    echo 'api-service tests passed successfully.'
                }
                failure {
                    echo 'api-service tests failed. Check logs for details.'
                }
            }
        }

        // Deploy user-service
        stage('Deploy user-service') {
            steps {
                echo 'Deploying user-service application...'
                // Replace with actual user-service deployment commands (e.g., Docker, Kubernetes, etc.)
                // sh 'docker-compose up -d' or any deployment command relevant to your user-service.
            }
            post {
                success {
                    echo 'user-service deployment succeeded.'
                }
                failure {
                    echo 'user-service deployment failed. Check logs for details.'
                }
            }
        }

        // Deploy api-service
        stage('Deploy api-service') {
            steps {
                echo 'Deploying api-service application...'
                // Replace with actual api-service deployment commands (e.g., Docker, Kubernetes, etc.)
                // sh 'docker-compose up -d' or any deployment command relevant to your api-service.
            }
            post {
                success {
                    echo 'api-service deployment succeeded.'
                }
                failure {
                    echo 'api-service deployment failed. Check logs for details.'
                }
            }
        }

        // Post-action stage (cleanup, notifications, or further tasks)
        stage('Post Action') {
            steps {
                echo 'Performing post-deployment actions...'
                // Example post-action (clean-up, sending notifications, etc.)
                // sh 'mvn clean' for cleanup or any post-action script here.
            }
            post {
                always {
                    echo 'This action runs after every stage regardless of success or failure.'
                }
                success {
                    echo 'Pipeline completed successfully.'
                }
                failure {
                    echo 'Pipeline failed. Please review logs for errors.'
                }
            }
        }
    }

    // Optional: you can define a 'post' block for actions that should happen after the pipeline finishes (e.g., notifications)
    post {
        success {
            echo 'Pipeline execution completed successfully!'
        }
        failure {
            echo 'Pipeline execution failed. Please review the logs.'
        }
    }
}
