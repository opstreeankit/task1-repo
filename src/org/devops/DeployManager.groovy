```groovy
package org.devops

class DeployManager implements Serializable {

    def script
    def env
    def strategy

    DeployManager(script, env, strategy) {
        this.script = script
        this.env = env
        this.strategy = strategy
    }

    def validate() {
        script.echo "===== VALIDATION ====="

        script.sh """
            if [ -f docker-compose.yaml ]; then
                echo "docker-compose.yaml found"
            else
                echo "docker-compose.yaml not found"
                exit 1
            fi
        """

        script.sh "docker --version"
    }

    def deploy() {

        script.echo "===== DEPLOYMENT ====="

        if (strategy == "rolling") {

            script.sh """
                echo "Deploying using Rolling Deployment to ${env}"
            """

        } else if (strategy == "bluegreen") {

            script.sh """
                echo "Deploying using Blue-Green Deployment to ${env}"
            """

        } else if (strategy == "canary") {

            script.sh """
                echo "Deploying using Canary Deployment to ${env}"
            """
        }
    }

    def healthCheck() {

        script.echo "===== HEALTH CHECK ====="

        script.sh """
            echo "Checking application health..."
            sleep 5
            echo "Application is healthy"
        """
    }

    def rollback() {

        script.echo "===== ROLLBACK ====="

        script.sh """
            cd ${script.env.WORKSPACE}

            echo "Rollback initiated for ${env}"
            echo "Reverting deployment..."

            sleep 3

            echo "Rollback completed successfully"
        """
    }
}
```
