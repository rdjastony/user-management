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
        stage('Checkout') {
            steps {
                git url: 'https://github.com/rdjastony/user-management', branch: 'main'
            }
        }

        stage('Build with Maven') {
            steps {
                dir('user-service/user') {  // Specify the relative path to the directory containing pom.xml
                    sh 'mvn clean install'
                }
            }
        }
    }
}
