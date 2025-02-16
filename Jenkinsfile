pipeline {
     agent {
            docker {
                image 'maven:3.8.6-jdk-11'
                args '-v $HOME/.m2:/root/.m2' // Opsiyonel: yerel Maven repository cache kullanmak için
            }
        }

    stages {
        stage('Checkout') {
            steps {
                // Örneğin, Git reposundan kodu çekiyoruz.
                git branch: 'main', url: 'https://github.com/ordersixtyfix/test-demo.git'
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
