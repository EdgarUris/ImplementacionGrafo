# Implementación de grafos en Java

Este proyecto implementa un grafo genérico: sus vértices pueden ser, por ejemplo, textos, números u objetos. Permite crear grafos dirigidos o no dirigidos, con o sin pesos, y trabajar con sus vértices, aristas y recorridos.

## Requisitos

- Java 23
- Maven
- 

## Crear un grafo

El constructor permite escoger si el grafo será dirigido y si sus aristas tendrán pesos. Un grafo dirigido permite recorrer una arista en el sentido indicado; uno no dirigido conecta sus vértices en ambos sentidos.

```java
import java.util.List;

import com.mycompany.grafo_261147.Grafo;
import com.mycompany.grafo_261147.TipoGrafo;

Grafo<String> grafo = new Grafo<>(TipoGrafo.DIRIGIDO_PONDERADO);
```

También se puede usar el constructor con dos opciones:

```java
Grafo<String> dirigidoPonderado = new Grafo<>(true, true);
Grafo<String> noDirigidoSinPesos = new Grafo<>(false, false);
```

Si se usa `new Grafo<>()` sin opciones, se crea un grafo no dirigido y sin pesos.

## Agregar y quitar vértices

`insertarVertice` agrega un vértice. Devuelve `true` si lo agregó y `false` si ya estaba en el grafo. `eliminarVertice` quita el vértice y también las aristas que lo conectaban.

```java
grafo.insertarVertice("A");
grafo.insertarVertice("B");
grafo.insertarVertice("C");

grafo.eliminarVertice("C");
```

Los vértices deben ser distintos de `null`. El método `obtenerVertices()` permite consultarlos en orden de inserción; no modifiques directamente la lista que devuelve.

## Agregar y quitar aristas

En un grafo ponderado se puede indicar el peso de cada arista. En un grafo sin pesos, o cuando se usa la versión sin peso, la arista se crea con peso `1`.

```java
grafo.insertarArista("A", "B", 2.0); // arista con peso 2
grafo.insertarArista("B", "C", 1.0); // arista con peso 1

grafo.eliminarArista("B", "C");
```

Los dos vértices deben existir antes de agregar una arista. En un grafo dirigido, `A` a `B` no significa que exista también una arista de `B` a `A`. En uno no dirigido, agregar o quitar una arista afecta a ambos sentidos. Si se agrega de nuevo una arista que ya existía, se actualiza su peso.

## Consultar e imprimir el grafo

Estos métodos imprimen información en la consola:

```java
grafo.imprimirMatrizAdyacencia(); // tabla que muestra las conexiones y sus pesos
grafo.imprimirAdyacentes("A");    // vértices conectados desde A
grafo.imprimirVertices();         // todos los vértices, en orden de inserción
```

En la matriz, `0` representa que no hay conexión y `1` representa una arista sin peso. En un grafo ponderado, se muestra el peso de cada arista. Para consultar estos datos sin imprimirlos, también están disponibles `getAdyacentesDe("A")` y `obtenerVertices()`.

## Recorridos y algoritmos

Los ejemplos de esta sección usan el siguiente grafo dirigido y ponderado:

```java
Grafo<String> grafo = new Grafo<>(TipoGrafo.DIRIGIDO_PONDERADO);

grafo.insertarVertice("A");
grafo.insertarVertice("B");
grafo.insertarVertice("C");
grafo.insertarVertice("D");

grafo.insertarArista("A", "B", 2.0);
grafo.insertarArista("A", "C", 5.0);
grafo.insertarArista("B", "C", 1.0);
grafo.insertarArista("B", "D", 4.0);
grafo.insertarArista("C", "D", 2.0);
```

### BFS (búsqueda en anchura)

Se ejecuta con `BusquedaEnAnchura`:

```java
grafo.BusquedaEnAnchura("A");
```

BFS explora primero los vecinos más cercanos al punto de inicio y luego continúa con los que están más lejos. Es útil para encontrar cuántos pasos separan dos vértices, revisar qué partes del grafo se pueden alcanzar o explorar conexiones por niveles. Este método imprime los vértices alcanzables desde el inicio.

### DFS (búsqueda en profundidad)

Se ejecuta con `BusquedaEnProfundidad`:

```java
grafo.BusquedaEnProfundidad("A");
```

DFS sigue un camino todo lo posible antes de volver atrás y probar otro. Es útil para recorrer el grafo, descubrir si hay conexiones entre sus partes y explorar caminos. Esta implementación empieza desde el vértice indicado y después recorre también los vértices que hayan quedado desconectados.

### Dijkstra

Se ejecuta con `dijkstra`, que devuelve un `Double[]` con la distancia mínima desde el inicio hasta cada vértice:

```java
Double[] distancias = grafo.dijkstra("A");
List<String> vertices = grafo.obtenerVertices();

for (int i = 0; i < distancias.length; i++) {
    String distancia = distancias[i] == Double.MAX_VALUE
            ? "inalcanzable"
            : distancias[i].toString();
    System.out.println(vertices.get(i) + ": " + distancia);
}
```

Las distancias aparecen en el mismo orden que `obtenerVertices()`. Es útil para encontrar la ruta de menor costo, por ejemplo, la ruta más corta entre lugares o la alternativa más económica entre opciones conectadas. En esta implementación se usa con grafos dirigidos y los pesos de las aristas deben ser cero o positivos. Un vértice al que no se llega conserva el valor `Double.MAX_VALUE`.

### Ordenamiento topológico

Se ejecuta con `ordenamientoTopologico`, que devuelve una lista de vértices:

```java
List<String> orden = grafo.ordenamientoTopologico();
System.out.println(orden);
```

Es útil para ordenar tareas que tienen dependencias: si una tarea debe hacerse antes que otra, aparecerá primero en la lista. Solo tiene sentido para grafos dirigidos sin ciclos (DAG). Si el grafo tiene ciclos, no existe un orden que satisfaga todas las dependencias; este método puede devolver una lista incompleta en ese caso.

### Caminos más cortos en un DAG

`caminosMasCortosDAG` devuelve un `double[]` con el costo mínimo desde el vértice de inicio hasta cada vértice:

```java
double[] distanciasDAG = grafo.caminosMasCortosDAG("A");
List<String> vertices = grafo.obtenerVertices();

for (int i = 0; i < distanciasDAG.length; i++) {
    String distancia = distanciasDAG[i] == Double.MAX_VALUE
            ? "inalcanzable"
            : Double.toString(distanciasDAG[i]);
    System.out.println(vertices.get(i) + ": " + distancia);
}
```

El arreglo también sigue el orden de `obtenerVertices()`. Este método sirve para buscar rutas de menor costo cuando las conexiones forman un DAG; a diferencia de Dijkstra, puede trabajar con pesos negativos, siempre que el grafo no tenga ciclos. Los vértices inalcanzables conservan `Double.MAX_VALUE`.
