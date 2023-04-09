pipeline {

    agent any
    environment {
        GIT_TOKEN = credentials('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')
    }
    /*
    {
        docker{image 'maven:3.5-alpine'}
    }
    */

    stages {
        stage("Cloning CarApp repository") {
            steps{
                git credentialsId: env.GIT_TOKEN, branch: 'master', url: 'https://github.com/GaborIreHun/CarApp.git'

                echo "************** repo cloned *****************"
            }                  
        }
         stage("Java version check") {
            steps{
               //sh "echo %JAVA_HOME%"
                sh "java --version"
            }      
        }

        stage("Mvn version check") {
            steps{
                sh "mvn --version"
            }      
        }
         stage("Code Stability") {
            steps{
                sh "mvn --version"
                sh "mvn clean package"
            }      
        }

        stage("Code Quality") {
            steps{
                sh "mvn checkstyle:checkstyle"
                recordIssues(tools: [checkStyle(pattern: '**/checkstyleresult.xml')])
            }          
        }
       
        stage("Unit Testing") {
            steps{
                sh "mvn test"
                recordIssues(tools: [junitParser(pattern: 'target/surefirereports/*.xml')])
            }       
        }

        stage("Security Testing") {
            steps{
                sh "mvn org.owasp:dependency-check-maven:check"
                publishHTML([allowMissing: false, alwaysLinkToLastBuild: false, keepAll: false, reportDir: 'target', reportFiles: 'dependency-check-report.html', reportName:'Dependency Check Report', reportTitles: ''])
            }       
        }

        stage("Sonarqube Analysis") {
            steps{
                sh "mvn sonar:sonar -Dsonar.host.url=${SONAR_URL} -Dsonar.login=${SONAR_USER} -Dsonar.password=${SONAR_PASSWORD} -Dsonar.java.binaries=."
            }    
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t carApp .'
            }
        }

        stage('Deploy with Kubernetes') {
            steps {
                withKubeConfig([credentialsId: 'kubeconfig']) {
                    sh 'kubectl apply -f deployment.yml'
                }
            }
        }
    }
}