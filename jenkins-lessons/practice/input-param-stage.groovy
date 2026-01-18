pipeline {
  agent any

  stages {
    stage('Build') {
      steps {
        sh 'echo build...'
      }
    }

    stage('Deploy (Approval Required)') {
      steps {
        script {
          def decision = input(
            id: 'deployGate',                      // stable id for restarts
            message: 'Deploy to PROD?',
            ok: 'Deploy',
            parameters: [
              choice(name: 'ENV', choices: ['staging', 'prod'], description: 'Target environment'),
              booleanParam(name: 'RUN_SMOKE', defaultValue: true, description: 'Run smoke tests after deploy?'),
              string(name: 'CHANGE_ID', defaultValue: '', description: 'Change / ticket ID')
            ]
          )

          // input returns a Map when multiple parameters exist
          env.DEPLOY_ENV = decision['ENV']
          env.RUN_SMOKE  = String.valueOf(decision['RUN_SMOKE'])
          env.CHANGE_ID  = decision['CHANGE_ID']
        }
      }
    }

    stage('Deploy') {
      when {
        expression { return env.DEPLOY_ENV != null }
      }
      steps {
        sh "echo Deploying to ${env.DEPLOY_ENV}, change=${env.CHANGE_ID}"
        script {
          if (env.RUN_SMOKE == 'true') {
            sh 'echo running smoke tests...'
          }
        }
      }
    }
  }
}
