pipeline{
    //  กำหนด ชื่อ,IP,.. ของ agent --> any : can run any agent
    agent any


    //
    environment{

        ORGANIZATION = "odds-booking-android"
        REGISTRY = "swr.ap-southeast-2.myhuaweicloud.com"
        TAG = "oddsbooking-android:${BRANCH_NAME}"
        APP_BUILD_TAG = "${REGISTRY}/${ORGANIZATION}/${TAG}"
    }

     stages{

            stage("Set environment"){
                steps{
                    sh """
                        cp /Users/Jerry/odds/oddsBooking-Android/fastlane/Fastfile
                    """
                }
            }
            stage("unit test"){
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
                        export PATH="/usr/local/opt/openjdk@11/bin:$PATH"
                        fastlane android distribute
                    """
                }
            }

        }
}
