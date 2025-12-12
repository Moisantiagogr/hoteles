pipeline {
    agent any

    stages {
        // 1. Parar servicios
        stage('Parando los servicios...') {
            steps {
                // CORREGIDO: 'exit /b 0' es de Windows. En Linux usamos 'true' para no fallar.
                sh 'docker compose -p hoteles down || true'
            }
        }

        // 2. Eliminar imágenes
        stage('Eliminando imágenes anteriores...') {
            steps {
                // CORREGIDO: Lógica traducida de Batch (Windows) a Bash (Linux)
                sh '''
                    # Guardamos los IDs de las imagenes en una variable
                    IMG_IDS=$(docker images --filter "label=com.docker.compose.project=hoteles" -q)
                    
                    # Verificamos si la variable esta vacia
                    if [ -z "$IMG_IDS" ]; then
                        echo "No hay imagenes por eliminar"
                    else
                        # Si hay imagenes, las borramos
                        docker rmi -f $IMG_IDS
                        echo "Imagenes eliminadas correctamente"
                    fi
                '''
            }
        }

        // 3. Obtener código
        stage('Obteniendo actualización...') {
            steps {
                checkout scm
            }
        }

        // 4. Construir y levantar
        stage('Construyendo y desplegando servicios...') {
            steps {
                sh 'docker compose up --build -d'
            }
        }
    }

    post {
        success {
            echo 'Pipeline ejecutado con éxito'
        }
        failure {
            echo 'Hubo un error al ejecutar el pipeline'
        }
        always {
            echo 'Pipeline finalizado'
        }
    }
}
