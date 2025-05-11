<?php
$servername = "localhost";
$username = "payment_user"; // remplace par le nom que tu as créé
$password = "c7YyDzq)wo0[b9iv";    // remplace par ton mot de passe
$dbname = "payment_system";

try {
    $conn = new PDO("mysql:host=$servername;dbname=$dbname;charset=utf8mb4", $username, $password);
    // set the PDO error mode to exception
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    echo "Connexion réussie !";
} catch(PDOException $e) {
    echo "Erreur de connexion : " . $e->getMessage();
}
?>
