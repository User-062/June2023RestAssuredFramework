pipeline 
{
    agent any
    
    tools{
    	maven 'maven'
        } 

    stages 
    {
        stage('Build') 
        {
            steps
            {
                 git 'https://github.com/jglick/simple-maven-project-with-tests.git'
                 sh "mvn -Dmaven.test.failure.ignore=true clean package"
            }
            post 
            {
                success
                {
                    junit '**/target/surefire-reports/TEST-*.xml'
                    archiveArtifacts 'target/*.jar'
                }
            }
        }

        
        
        stage("Deploy to QA"){
            steps{
                echo("deploy to qa done")
            }
        }
             
        stage('Regression API Automation Test') {
        	 steps{
        	 	catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
        	 	git 'https://github.com/User-062/June2023RestAssuredFramework.git'
                sh "mvn clean test -Dsurefire.suitXmlFiles="/Users/mac/Desktop/APITesting/June2023RestAssuredFramework/src/test/resources/testrunners/testng_regression.xml"
            }
        }
      }
       
       stage('Publish Allure Report'){
            steps{
                 script{
                 	allure([
                 		includeProperties: false,
                 		jdk: '',
                 		properties: [],
                 		reportBuildPolicy: 'ALWAYS',
                 		results: [[path: '/allure-results']]
                 	])
                 }		
              } 
           }     		
           stage('Publish  Extent Report'){
            	steps{
                     publishHTML([allowMissing: false,
                                  alwaysLinkToLastBuild: false, 
                                  keepAll: false, 
                                  reportDir: 'reports', 
                                  reportFiles: 'APIExecutionReport.html', 
                                  reportName: 'API HTML Regression Extent Report', 
                                  reportTitles: ''])
           
                 	}	
                     	
            }
                
             stage("Deploy to STAGE"){
            	steps{
                	echo("deploy to STAGE done")
            }   
          }      
          
           stage('SanityI Automation Test') {
        	 steps{
        	 	catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
        	 	git 'https://github.com/User-062/June2023RestAssuredFramework.git'
                sh "mvn clean test -Dsurefire.suitXmlFiles="/Users/mac/Desktop/APITesting/June2023RestAssuredFramework/src/test/resources/testrunners/testng_regression.xml"
            }
        }
      }
      
      stage('Publish  Extent Report sanity'){
            	steps{
                     publishHTML([allowMissing: false,
                                  alwaysLinkToLastBuild: false, 
                                  keepAll: false, 
                                  reportDir: 'reports', 
                                  reportFiles: 'APIExecutionReport.html', 
                                  reportName: 'API HTML Regression Extent Report', 
                                  reportTitles: ''])
           
                 	}	
                     	
            }    
      
            stage("Deploy to PROD"){
            	steps{
                	echo("deploy to PROD done")
            }
        }
     
     }
  }          
        