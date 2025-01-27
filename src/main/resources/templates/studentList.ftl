<!DOCTYPE html>
<html>
<head>
    <title>Student Management</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</head>

<body>
    <div class="container mt-5">
        <div class="row">
            <!-- Create New Student Form (left side) -->
            <div class="col-md-3" style="position: fixed; top: 30%; height: 100vh; overflow-y: auto;">
                <h3>Create New Student</h3>
                <form action="/api/v1/student-view" method="post">
                    <div class="mb-3">
                        <label for="name" class="form-label">Name</label>
                        <input type="text" class="form-control" id="name" name="name" required>
                    </div>
                    <div class="mb-3">
                        <label for="age" class="form-label">Age</label>
                        <input type="number" class="form-control" id="age" name="age" required>
                    </div>
                    <button type="submit" class="btn btn-primary">Submit</button>
                </form>
            </div>

            <div class="col-md-4"> </div>
            <!-- Student List (right side) -->
            <div class="col-md-8">
                <h1 class="text-center">Students</h1>
                <div class="table-responsive" style="max-height: 450px; overflow-y: auto;">
                    <table class="table table-bordered table-striped">
                        <thead class="table-dark">
                            <tr>
                                <th>ID</th>
                                <th>Name</th>
                                <th>Age</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <#list students as student>
                                <tr>
                                    <td>${student.id}</td>
                                    <td>${student.name}</td>
                                    <td>${student.age}</td>
                                    <td>
                                        <button type="button" class="btn btn-warning btn-sm edit-btn"
                                                data-id="${student.id}"
                                                data-name="${student.name}"
                                                data-age="${student.age}">
                                            Edit
                                        </button>
                                        <button type="button" class="btn btn-danger btn-sm delete-btn" data-id="${student.id}">
                                            Delete
                                        </button>
                                    </td>
                                </tr>
                            </#list>
                        </tbody>
                    </table>
                </div>
            </div>

        </div>
    </div>

    <!-- Edit Student Modal -->
    <div class="modal fade" id="editModal" tabindex="-1" aria-labelledby="editModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <form id="editStudentForm">
                    <div class="modal-header">
                        <h5 class="modal-title" id="editModalLabel">Edit Student</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <input type="hidden" id="edit-id" name="id">
                        <div class="mb-3">
                            <label for="edit-name" class="form-label">Name</label>
                            <input type="text" class="form-control" id="edit-name" name="name" required>
                        </div>
                        <div class="mb-3">
                            <label for="edit-age" class="form-label">Age</label>
                            <input type="number" class="form-control" id="edit-age" name="age" required>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                        <button type="submit" class="btn btn-primary">Save changes</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <script>
        $(document).ready(function () {
            // Open the modal and populate it with the selected student data
            $('.edit-btn').on('click', function () {
                const id = $(this).data('id');
                const name = $(this).data('name');
                const age = $(this).data('age');
                console.log("Student ID:", id);

                $('#edit-id').val(id);
                $('#edit-name').val(name);
                $('#edit-age').val(age);

                // Show the modal
                console.log("Button clicked, opening modal for ID: " + id);
                $('#editModal').modal('show');
            });

            // Handle the form submission for editing a student
                $('#editStudentForm').on('submit', function (e) {
                e.preventDefault();

                const id = $('#edit-id').val();
                const name = $('#edit-name').val();
                const age = $('#edit-age').val();

                const dataToSend = JSON.stringify({id: id, name: name, age: age });
                console.log("Sending data as JSON: ", dataToSend);

                $.ajax({
                    url: "/api/v1/students/"+id,
                    method: 'PUT',
                    contentType: 'application/json', // Specify JSON content type
                    data: dataToSend, // Send JSON data
                    success: function () {
                        console.log("Student updated successfully!");
                        alert('Student updated successfully!');
                        location.reload();
                    },
                    error: function (xhr, status, error) {
                        console.error("Error updating student:", xhr.responseText);
                        console.error("Status: " + status);
                        console.error("Error: " + error);
                        alert('Failed to update student.');
                    }
                });
            });

            // Delete student
            $('.delete-btn').on('click', function () {
                const id = $(this).data('id');

                if (confirm('Are you sure you want to delete this student?')) {
                    $.ajax({
                        url: "/api/v1/students/"+id,
                        method: 'DELETE',
                        success: function () {
                            console.log("Student deleted successfully!");
                            alert('Student deleted successfully!');
                            location.reload();
                        },
                        error: function (xhr, status, error) {
                            console.error("Error deleting student:", xhr.responseText);
                            console.error("Status: " + status);
                            console.error("Error: " + error);
                            alert('Failed to delete student.');
                        }
                    });
                }
            });
        });
    </script>
</body>

</html>
