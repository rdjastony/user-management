pipeline {
    agent any

    environment {
        JAVA_HOME = '/usr/lib/jvm/java-17-openjdk'
        // Ensure the Maven tool name is correct
        MAVEN_HOME = tool name: 'Maven 3', type: 'Tool'
    }

    stages {
        stage('Checkout') {
            steps {
                // Checkout the code from your repository
                git 'https://github.com/rdjastony/user-management.git'
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
            // Wrap cleanWs in a node block as it requires workspace context
            node {
                cleanWs()  // Clean up the workspace
            }
        }
    }
}
