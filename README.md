<h1><a href="https://www.github.com/Daniel-Carbajal/se333-assignment5-Part-1">Assignment 5 Part 1</a> Write-Up</h1>
<h4>Author: Daniel Carbajal</h4>
<h4>Course: SE333 Software Testing and Quality Assurance</h4>

[![SE333 CI](https://github.com/Daniel-Carbajal/se333-assignment5/actions/workflows/SE333_CI.yml/badge.svg)](https://github.com/Daniel-Carbajal/se333-assignment5/actions/workflows/SE333_CI.yml)
<h2>Description</h2>
This part of the project implements testing and continuous integration for the Amazon shopping cart system. The system calculates the total cost of items in a cart using pricing rules such as:
<ul>
    <li>Regular Item Cost</li>
    <li>Electronics Surcharge</li>
    <li>Delivery Fee Tiers</li>
</ul>

This part of the assignment focuses on writing unit (with Mockito) and integration tests and setting up a CI pipeline to automatically run tests and generate reports.

<h2>Testing</h2>
Two types of tests were implemented for Amazon class
<h4>Unit Tests</h4>
Unit tests isolate the Amazon class using Mockito to mock dependencies such as the ShoppingCart and PriceRule objects. These tests verify that:
<ul>
    <li>Items are added to the cart correctly</li>
    <li>The calculate() method aggregates rule results correctly</li>
    <li>Each rule is invoked exactly once</li>
    <li>The cart is queried for items during calculation</li>
</ul>

<h4>Integration Tests</h4>
Integration tests use the real systems components without mocking. These tests use the ShoppingCartAdopter and an in memory HSQL database to verify that:
<ul>
    <li>Items are stored and retrieved correctly</li>
    <li>Pricing rules interact correctly with the cart</li>
    <li>The final price calculation works end-to-end</li>
</ul>

<h2>Continuous Integration Pipeline</h2>
GitHub Actions is used to automatically build and test the project when code is pushed to the main branch.
The CI pipeline performs the following steps:
<ol>
    <li>Checks out repository</li>
    <li>Sets up Java</li>
    <li>Runs Checkstyle during the Maven validate phase</li>
    <li>Executes all unit and integration tests</li>
    <li>Generates a JaCoCo code coverage report</li>
    <li>Uploads Checkstyle and JaCoCo reports as workflow artifacts</li>
</ol>
