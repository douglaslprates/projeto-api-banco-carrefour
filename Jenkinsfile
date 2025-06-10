pipeline {
	agent any

	tools {
		maven 'Maven-3.8.6'
        jdk 'JDK-11'
    }

    stages {
		stage('Checkout') {
			steps {
				git 'https://github.com/douglaslprates/projeto-api-banco-carrefour.git'
            }
		}
		stage('Build & Test') {
			steps {
				sh 'mvn clean test'
            }
        }

        stage('Allure Report') {
			steps {
				allure([
                    includeProperties: false,
                    jdk: '',
                    properties: [],
                    reportBuildPolicy: 'ALWAYS',
                    results: [[path: 'target/allure-results']]
                ])
            }
        }
    }

    post {
		always {
			archiveArtifacts artifacts: 'target/allure-results/**/*', fingerprint: true
            cleanWs()
        }
        success {
			script {
				emailext body: 'Testes executados com sucesso! Relatório: ${BUILD_URL}Allure_20Report',
                subject: '[SUCCESS] API Tests - ${JOB_NAME}',
                to: 'team@example.com'
            }
        }
        failure {
			script {
				emailext body: 'Falha nos testes: ${BUILD_URL}console',
                subject: '[FAILURE] API Tests - ${JOB_NAME}',
                to: 'team@example.com'
            }
        }
    }
}

