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
                node {  // Wrap the build steps inside the node block
                    // Build the project using Maven
                    script {
                        sh "'${MAVEN_HOME}/bin/mvn' clean install -DskipTests"
                    }
                }
            }
        }

        stage('Test') {
            steps {
                node {  // Wrap the test steps inside the node block
                    // Run the tests using Maven
                    script {
                        sh "'${MAVEN_HOME}/bin/mvn' test"
                    }
                }
            }
        }

        stage('Package') {
            steps {
                node {  // Wrap the packaging steps inside the node block
                    // Create a JAR file using Maven
                    script {
                        sh "'${MAVEN_HOME}/bin/mvn' package -DskipTests"
                    }
                }
            }
        }

        stage('Deploy') {
            steps {
                node {  // Wrap the deploy steps inside the node block
                    // Example deploy step, adjust to your deployment method
                    script {
                        sh "docker build -t user-management-api ."
                        sh "docker run -d -p 8080:8080 user-management-api"
                    }
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
            node {  // Wrap the cleanup inside the node block
                cleanWs()
            }
        }
    }
}
