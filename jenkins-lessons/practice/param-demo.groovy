pipeline {
    agent any

    stages {
        stage('RUN_TEST') {
            when {
                expression { 
                    params.RUN_TEST == true 
                }
            }

            steps {
                echo 'Hello World'
            }
        }
        
        stage('Deploy Code') {
            steps {
                echo 'Deploying Code Directly!!!'
            }
        }
    }
}