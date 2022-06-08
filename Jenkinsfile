pipeline{
    //  กำหนด ชื่อ,IP,.. ของ agent --> any : can run any agent
    agent any

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
                steps{
                    sh """
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
