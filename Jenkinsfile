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
}