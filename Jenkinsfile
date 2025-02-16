pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                // Örneğin, Git reposundan kodu çekiyoruz.
                git url: 'https://github.com/kullaniciAdi/projeAdi.git', branch: 'main'
            }
        }
        stage('Build') {
            steps {
                sh 'mvn clean compile'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
        stage('Package') {
            steps {
                sh 'mvn package'
            }
        }
    }
    post {
        always {
            junit '**/target/surefire-reports/*.xml'
        }
    }
}