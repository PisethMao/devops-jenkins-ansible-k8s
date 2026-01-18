pipeline {
    agent none

    stages {
        stage('Hello from Multi-Node') {
            agent {
                label 'ssh-agent'
            }

            steps {
                echo 'This is a multi-node pipeline example.'
                sh """
                    curl ifconfig.me
                """
            }
        }

        stage('Worker-01') {
            agent {
                label 'Worker-01'
            }

            steps {
                echo 'Executing on Worker-01'
                sh """
                    curl ifconfig.me
                """
            }
        }
    }
}