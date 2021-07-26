pipeline {
    agent none
    stages {
        stage('Clone') {
            agent {
                label 'master'
            }
            steps {
                echo "1. Git Clone Stage"
                git url: "https://github.com/Arsekl/cloud_native_final.git"

            }

        }
        stage('Maven Build') {
            agent {
                docker {
                    image 'maven:latest'
                    args '-v /root/.m2:/root/.m2'

                }

            }
            steps {
                echo "2. Maven Build Stage"
                sh 'mvn -B clean package -Dmaven.test.skip=true'

            }

        }
        stage('Image Build') {
            agent {
                label 'master'

            }
            steps {
                echo "3. Image Build Stage"
                sh 'docker build -f Dockerfile --build-arg jar_name=target/cloud_native_final-1.0-SNAPSHOT.jar -t cloud-native-project:${BUILD_ID} . '
                sh 'docker tag cloud-native-project:${BUILD_ID} harbor.edu.cn/cn202101/cloud-native-project:${BUILD_ID}'

            }

        }
        stage('Push') {
            agent {
                label 'master'

            }
            steps {
                echo "4. Push Docker Image Stage"
                sh "docker login --username=cn202101 harbor.edu.cn -p cn202101"
                sh "docker push harbor.edu.cn/cn202101/cloud-native-project:${BUILD_ID}"

            }

        }

    }

}

node('slave') {
    container('jnlp-kubectl') {
        stage('connect'){
            sh 'curl "http://p.nju.edu.cn/portal_io/login" --data "username=181098100&password=hjm986667250"'
        }
        stage('Git Clone') {
            git url: "https://github.com/Arsekl/cloud_native_final.git"

        }
        stage('YAML') {
            echo "5. Change YAML File Stage"
            sh 'sed -i "s#{VERSION}#${BUILD_ID}#g" cloud-native-project.yaml'

        }
        stage('Deploy') {
            echo "6. Deploy To K8s Stage"
            sh 'kubectl apply -f cloud-native-project.yaml -n cn202101'
            sh 'kubectl apply -f cloud-native-project-serviceMonitor.yaml'

        }
//         stage('RTF Test'){
//             echo "RTF Test Stage"
//             sh 'kubectl apply -f ./jenkins/scripts/rtf.yaml -n cn202101'
//
//         }
    }

}