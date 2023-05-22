<div th:switch="${albums}">
    <h2 th:case="null">No albums yet!</h2>
        <div th:case="*">
            <h2>Albums</h2>
            <table>
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Artist</th>
                        <th>Sample Audio</th>
                        <th>Date Release</th>
                        <th>Price</th>
                        <th>Edit</th>
                        <th>Delete</th>
                    </tr>
                </thead>
                <tbody>
                <tr th:each="album : ${albums}">
                    <td th:text="${album.albumNm}"></td>
                    <td th:text="${album.artistNm}"></td>
                    <td th:text="${album.sampleAu}"></td>
                    <td th:text="${album.dateRel}"></td>
                    <td th:text="${album.price}"></td>
                    <td><a th:href="@{/edit/{id}(id=${album.id})}">Edit</a></td>
                    <td><a th:href="@{/delete/{id}(id=${album.id})}">Delete</a></td>
                </tr>
            </tbody>
        </table>
    </div>      
    <p><a href="/signup">Add a new album</a></p>
</div>