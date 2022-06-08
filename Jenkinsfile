pipeline{
    //  กำหนด ชื่อ,IP,.. ของ agent --> any : can run any agent
    agent any


    //
//     environment{
//
//         ORGANIZATION = "odds-booking-android"
//         REGISTRY = "swr.ap-southeast-2.myhuaweicloud.com"
//         TAG = "oddsbooking-android:${BRANCH_NAME}"
//         APP_BUILD_TAG = "${REGISTRY}/${ORGANIZATION}/${TAG}"
//     }
     stages{
            stage("Checkout SCM"){
                steps{
                    checkout scm
                }
            }
            stage("Set Environment"){
                steps{
                    sh """
                        cp /Users/Jerry/odds/oddsBooking-Android/local.properties
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
  post {
      always {
          script {
              def JENKINS_VERSION = Jenkins.instance.getVersion().toString()
              def DISCORD_NOTIFIER_VERSION = Jenkins.instance.pluginManager.getPlugin('discord-notifier').getVersion()

              def changes = getChanges()
              def artifacts = getArtifacts()

              discordSend description: "**Build:** [${BUILD_NUMBER}](${BUILD_URL})\n**Status:** [${currentBuild.currentResult.toLowerCase()}](${BUILD_URL})\n\n**Changes:**\n"+changes+"\n\n**Artifacts:**\n"+artifacts+"\n",
                  link: env.BUILD_URL,
                  result: currentBuild.currentResult,
                  title: JOB_NAME + " #" + env.BUILD_NUMBER,
                  footer: "Jenkins v"+ JENKINS_VERSION +", Discord Notifier v"+ DISCORD_NOTIFIER_VERSION,
                  successful: currentBuild.resultIsBetterOrEqualTo('SUCCESS'),
                  webhookURL: "https://discord.com/api/webhooks/979228784064614410/Ynmo3Zd9WbP8PvulM87fO0iZlKg0PTxnYuc73OLe3cLMzI__n9gNCsKR7GCfpIAZmda8"
            }
        }
    }
}

@NonCPS
def getArtifacts() {
    def msg = ""
    def artifactUrl = env.BUILD_URL + "artifact/"

    currentBuild.rawBuild.getArtifacts().each {
        filename = it.getFileName()
        msg += "- ${artifactUrl}${it.getFileName()}\n"
    }

    if (msg.isEmpty()) {
        msg = "n/a"
    }

    return msg.trim()
}

@NonCPS
def getChanges() {
    def changes = ""
    def changeLogSets = currentBuild.changeSets
    for (int i = 0; i < changeLogSets.size(); i++) {
        def entries = changeLogSets[i].items
        for (int j = 0; j < entries.length; j++) {
            def entry = entries[j]
            def commitId = entry.commitId.substring(0,8)
            changes += "- `${commitId}` *${entry.msg} - ${entry.author}*\n"
        }
    }

    if (changes.isEmpty()) {
        changes = "No changes."
    }
    return changes.trim()
}

