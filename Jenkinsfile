pipeline {
    agent any

    environment {
        JAVA_HOME = tool name: 'JDK 17'
        MAVEN_HOME = tool name: 'Maven 3', type: 'maven'
        PATH = "${JAVA_HOME}/bin:${MAVEN_HOME}/bin:${env.PATH}"
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/rdjastony/user-management.git'
            }
        }

        stage('Build') {
            steps {
                script {
                    sh "${MAVEN_HOME}/bin/mvn -V"
                    sh "${MAVEN_HOME}/bin/mvn clean install -DskipTests"
                }
            }
        }

        stage('Test') {
            steps {
                script {
                    sh "${MAVEN_HOME}/bin/mvn test"
                }
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Package') {
            steps {
                script {
                    sh "${MAVEN_HOME}/bin/mvn package -DskipTests"
                    archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                }
            }
        }

        stage('Deploy') {
            steps {
                script {
                    // Clean up any previous container
                    sh '''
                        docker stop user-management-app || true
                        docker rm user-management-app || true
                        docker rmi user-management-api || true
                    '''

                    // Build new Docker image
                    sh 'docker build -t user-management-api .'

                    // Run container
                    sh 'docker run -d -p 8080:8080 --name user-management-app user-management-api'
                }
            }
        }
    }

    post {
        success {
            echo '✅ Build and deployment succeeded.'
        }
        failure {
            echo '❌ Build or deployment failed.'
        }
        always {
            echo '🧹 Cleaning workspace...'
            cleanWs()
        }
    }
}
