pipeline {
    agent any

    environment {
        // Set environment variables, such as Java version or Maven options if needed
        JAVA_HOME = '/usr/lib/jvm/java-17-openjdk'
        MAVEN_HOME = tool name: 'Maven 3', type: 'Tool'
    }

    stages {
        stage('Checkout') {
            steps {
                // Checkout the code from your repository
                git 'https://github.com/your-username/your-repository.git' branch: 'main'
            }
        }

        stage('Build') {
            steps {
                // Build the project using Maven
                script {
                    sh "'${MAVEN_HOME}/bin/mvn' clean install -DskipTests"
                }
            }
        }

        stage('Test') {
            steps {
                // Run the tests using Maven
                script {
                    sh "'${MAVEN_HOME}/bin/mvn' test"
                }
            }
        }

        stage('Package') {
            steps {
                // Create a JAR file using Maven
                script {
                    sh "'${MAVEN_HOME}/bin/mvn' package -DskipTests"
                }
            }
        }

        stage('Deploy') {
            steps {
                // Example deploy step, adjust to your deployment method
                script {
                    sh "docker build -t user-management-api ."
                    sh "docker run -d -p 8080:8080 user-management-api"
                }
            }
        }
    }

    post {
        success {
            echo 'Build and deployment succeeded.'
        }
        failure {
            echo 'Build or deployment failed.'
        }
        always {
            // Clean up actions if needed, like archiving build artifacts or reports
            cleanWs()
        }
    }
}
