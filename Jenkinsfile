pipeline{
    //  กำหนด ชื่อ,IP,.. ของ agent --> any : can run any agent
    agent none

    environment{
        PATH = "/usr/local/bin:/usr/bin:/bin:/usr/sbin:/sbin:/Library/Apple/usr/bin:/Users/Jerry/.rvm/bin"
    }

     stages{

            stage("Set Environment"){
                steps{
                    sh """
                        echo $PATH
                       """
                }
            }
            stage("unit test"){
                agent{ docker 'gradle:7.4-jdk17-alpine'}
                steps{
                    sh """
                        export PATH="/usr/local/opt/openjdk@11/bin:$PATH"
                        ./gradlew clean testDevDe
                    """
                }
            }
            stage("build and push to firebase"){
                steps{
                    sh """
                        fastlane android distribute
                    """
                }
            }
        }
}
