pipeline {
    agent any

    stages {
        stage('Clone Code') {
            steps {
                git 'https://github.com/PisethMao/reactjs-template-product'
                sh 'ls -la'
                sh 'pwd'
                sh '''
                    echo "Changing directory to reactjs-template-product"
                    ls -la
                '''
            }
        }

        stage('Build') {
            steps {
                sh '''
                    docker build -t jenkins-react-pipeline .
                '''
            }
        }

        stage('Deploy') {
            steps {
                sh ''''
                    docker run -dp 3000 --name reactjs-cont jenkins-react-pipeline
                '''
            }
        }
    }
}