pipeline {
    agent any

    environment {
        // Prevents Gradle from leaving background daemons eating your Mac's RAM
        GRADLE_OPTS = '-Dorg.gradle.daemon=false -Dorg.gradle.jvmargs="-Xmx4g"'
    }

    stages {
        stage('1. Permissions') {
            steps {
                // Ensures the Gradle wrapper script is executable on macOS
                sh 'chmod +x ./gradlew'
            }
        }

        stage('2. Lint Check') {
            steps {
                sh './gradlew lintDebug'
            }
        }

        stage('3. Run Unit Tests') {
            steps {
                sh './gradlew testDebugUnitTest'
            }
            post {
                always {
                    junit '**/build/test-results/testDebugUnitTest/*.xml'
                }
            }
        }

        stage('4. Build Debug APK') {
            steps {
                sh './gradlew assembleDebug'
            }
            post {
                success {
                    archiveArtifacts artifacts: 'app/build/outputs/apk/debug/*.apk', fingerprint: true
                }
            }
        }
    }
}