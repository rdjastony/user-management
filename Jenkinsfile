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

        // Build the project with Maven
        stage('Build with Maven') {
            steps {
                dir('user-service/user') {  // Specify the relative path to the directory containing pom.xml
                    sh 'mvn clean install'
                }
            }
        }

        // Test the application (run unit tests or integration tests)
        stage('Test') {
            steps {
                dir('user-service/user') {
                    // Run the tests with Maven
                    sh 'mvn test'
                }
            }
            post {
                success {
                    echo 'Tests passed successfully.'
                }
                failure {
                    echo 'Tests failed. Check logs for details.'
                }
            }
        }

        // Deploy the application (for this example, we'll just simulate it with an echo command)
        stage('Deploy') {
            steps {
                echo 'Deploying the application...'
                // Example of deployment step (replace with actual deployment commands)
                // sh 'docker-compose up -d' or any deployment command relevant to your project
            }
            post {
                success {
                    echo 'Deployment succeeded.'
                }
                failure {
                    echo 'Deployment failed. Check logs for details.'
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
