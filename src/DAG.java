import java.util.*;

public class DAG<T> {
    private List<Vertex<T>> vertices = new ArrayList<>();

    public Vertex<T> createVertex(T value) {
        Vertex<T> v = new Vertex<>(value);
        vertices.add(v);
        return v;
    }

    public void createEdge(Vertex<T> from, Vertex<T> to) {
        // добавляем в список смежности только в одном направлении
        from.getAdjacent().add(to);
    }

    public int path(Vertex<T> from, Vertex<T> to) {
        Map<Vertex<T>, Integer> paths = new HashMap<>(); // по городу даёт количество полётов чтобы до него
        // добраться из from
        paths.put(from, 0); // добраться из пункта отправления до него же самого можно вообще не летая

        Queue<Vertex<T>> queue = new ArrayDeque<>(); // очередь обхода вершин
        Set<Vertex<T>> added = new HashSet<>(); // запоминаем все когда-либо побывавшие в очереди обхода
                                                 // вершины чтобы не дублироваться
        queue.add(from);                  // начинать будем из города отправления

        while (!queue.isEmpty()) {        // пока очередь не пуста
            Vertex<T> v = queue.remove();  // вынимаем следующий элемент из очереди
            if (v.equals(to)) {
                return paths.get(v);       //   если v это город назначения, то возвращаем
                                           // из paths готовый ответ для этой вершины
            } else {
                for (Vertex<T> next : v.getAdjacent()) { // перебираем вершины по дугам из v
                    if (added.contains(next)) { // если такую вершину уже добавляли в очередь
                        // (воспользуйтесь коллекцией added), то пропускаем её.
                        continue;
                    } else {
                        added.add(next); // иначе, добавляем её в очередь, added и paths (подумайте, сколько
                        // перелётов будет до этого города,
                        paths.put(next, paths.get(v) + 1); // если мы знаем сколько перелётов до v)
                        
                        queue.add(next);
                    }
                }
            }
        }

        return -1;
    }

}
