import React, { useEffect, useState } from "react";
import { createRoot } from "react-dom/client";

const API_URL = "http://localhost:8080/api/movies";

function App() {
  const [movies, setMovies] = useState([]);
  const [pageNumber, setPageNumber] = useState(0);
  const pageSize = 5;

  const [totalPages, setTotalPages] = useState(0);
  const [totalElements, setTotalElements] = useState(0);

  const [sortField, setSortField] = useState("");

  // Load movies with pagination
  const loadMovies = () => {
    fetch(`${API_URL}/pagination/${pageNumber}/${pageSize}`)
      .then((res) => res.json())
      .then((data) => {
        console.log("Pagination Response:", data);
        setMovies(data.content || []);
        setTotalPages(data.totalPages || 0);
        setTotalElements(data.totalElements || 0);
      })
      .catch((err) => console.error(err));
  };

  // Load movies with sorting
  const loadSortedMovies = (field) => {
    setSortField(field);

    if (!field) {
      loadMovies(); // Reset to pagination
      return;
    }

    fetch(`${API_URL}/sort/${field}`)
      .then((res) => res.json())
      .then((data) => {
        console.log("Sorting Response:", data);

        if (Array.isArray(data)) {
          setMovies(data);
        } else if (Array.isArray(data.content)) {
          setMovies(data.content);
        } else {
          setMovies([]);
        }

        setTotalElements(data.length || data.totalElements || 0);
        setTotalPages(1); // Sorting shows full list on single page
      })
      .catch((err) => console.error(err));
  };

  // Initial load + load when page changes
  useEffect(() => {
    if (sortField === "") {
      loadMovies();
    }
  }, [pageNumber]);

  // Initial load immediately
  useEffect(() => {
    loadMovies();
  }, []);

  return (
    <div style={{ padding: "30px", fontFamily: "Arial" }}>
      <h1 style={{ textAlign: "center" }}>🎬 Movie Dashboard</h1>

      <div style={{ marginTop: "20px" }}>

        {/* Sorting Dropdown */}
        <div style={{ marginBottom: "10px" }}>
          <label><b>Sort By:</b></label>
          <select
            value={sortField}
            onChange={(e) => loadSortedMovies(e.target.value)}
            style={{ marginLeft: "10px", padding: "5px" }}
          >
            <option value="">Default (Pagination)</option>
            <option value="title">Title</option>
            <option value="language">Language</option>
            <option value="description">Description</option>
          </select>
        </div>

        <p><b>Total Movies:</b> {totalElements}</p>

        {/* Table */}
        <table border="1" width="100%" cellPadding="10">
          <thead>
            <tr style={{ background: "#eee" }}>
              <th>Title</th>
              <th>Language</th>
              <th>Description</th>
            </tr>
          </thead>

          <tbody>
            {movies.map((movie) => (
              <tr key={movie.id}>
                <td>{movie.title}</td>
                <td>{movie.language}</td>
                <td>{movie.description}</td>
              </tr>
            ))}
          </tbody>
        </table>

        {/* Pagination Controls */}
        {sortField === "" && (
          <div style={{ marginTop: "20px", textAlign: "center" }}>
            <button
              onClick={() => setPageNumber((prev) => Math.max(prev - 1, 0))}
              disabled={pageNumber === 0}
              style={{ padding: "8px 14px", marginRight: "10px" }}
            >
              Previous
            </button>

            <span>Page {pageNumber + 1} of {totalPages}</span>

            <button
              onClick={() => setPageNumber((prev) => (prev + 1 < totalPages ? prev + 1 : prev))}
              disabled={pageNumber + 1 >= totalPages}
              style={{ padding: "8px 14px", marginLeft: "10px" }}
            >
              Next
            </button>
          </div>
        )}

      </div>
    </div>
  );
}

const root = createRoot(document.getElementById("root"));
root.render(<App />);
