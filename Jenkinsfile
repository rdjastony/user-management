pipeline {
    agent any  // This means Jenkins will use any available node/agent.

    environment {
        JAVA_HOME = '/usr/lib/jvm/java-17-openjdk' // Ensure this path is correct for your agent(s)
        // IMPORTANT: Make sure a Maven tool named 'Maven 3' is configured
        // in Manage Jenkins -> Global Tool Configuration
        MAVEN_HOME = tool name: 'Maven 3', type: 'maven' // Changed type to 'maven' for clarity, 'Tool' also works
    }

    stages {
       stage('Checkout') {
           steps {
               git branch: 'main', url: 'https://github.com/rdjastony/user-management.git'
           }
       }

        stage('Build') {
            steps {
                // Build the project using Maven
                script {
                    // Ensure Maven is runnable and JAVA_HOME is correctly set
                    sh "'${MAVEN_HOME}/bin/mvn' -V" // Print Maven version for debugging
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
            // Optional: Add post actions for test reports, e.g., using JUnit plugin
            // post {
            //     always {
            //         junit 'target/surefire-reports/*.xml'
            //     }
            // }
        }

        stage('Package') {
            steps {
                // Create a JAR file using Maven
                script {
                    sh "'${MAVEN_HOME}/bin/mvn' package -DskipTests"
                    // Optional: Archive the artifact
                    // archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                }
            }
        }

        stage('Deploy') {
            // Ensure Docker is installed and running on the agent executing this stage
            // Ensure the Jenkins user has permissions to run docker commands
            steps {
                // Example deploy step, adjust to your deployment method
                script {
                    // Assumes a Dockerfile exists in the root directory
                    sh "docker build -t user-management-api ."
                    // Basic run command - consider more robust deployment strategies
                    // e.g., stopping/removing old containers, using docker-compose, deploying to Kubernetes etc.
                    sh "docker run -d -p 8080:8080 --name user-management-app user-management-api" // Added a container name
                }
            }
            // Optional: Add cleanup for the container in post actions if needed
            // post {
            //     always {
            //          sh "docker stop user-management-app || true"
            //          sh "docker rm user-management-app || true"
            //     }
            // }
        }
    }

    post {
        success {
            echo 'Build and deployment succeeded.'
            // Add success notifications if needed (e.g., email, Slack)
        }
        failure {
            echo 'Build or deployment failed.'
            // Add failure notifications if needed
        }
        always {
            echo 'Pipeline finished. Cleaning workspace.'
            // Wrap cleanWs in a node block to give it a workspace context
            // This ensures the workspace on the agent used for the build is cleaned
            node(env.NODE_NAME ?: 'any') { // Try to use the same node if possible, fallback to 'any'
                 cleanWs()
            }
        }
    }
}