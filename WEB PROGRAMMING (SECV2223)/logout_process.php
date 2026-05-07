<?php
// logout_process.php 
require_once 'config.php';

session_unset();
session_destroy();

echo "success";
?>