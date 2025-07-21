// Good: Descriptive names, consistent formatting, error handling
function fetchData(url) {
    try {
        // Fetch data from the API
        const response = fetch(url);
        return response;
    } catch (error) {
        console.error("Failed to fetch data:", error.message);
    }
}

// Bad: Vague names, no error handling
function f(u) { return fetch(u); }
