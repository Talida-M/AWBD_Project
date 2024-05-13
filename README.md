# AWBD_Project

Project made by Alexandra Ion and Talida-Marina Dobre, gr 405
 
Short Description:
This project serves as the foundation for a comprehensive mental health application, offering users a seamless experience in creating and overseeing vital aspects of their mental well-being. Within this system, users can easily discover specialized healthcare professionals and schedule consultations, whether they prefer online or in-person appointments. Furthermore, users have the ability to provide feedback through reviews, and patients can maintain a personal journal with the option to selectively share specific entries with their chosen specialists, facilitating collaborative care and enhancing overall mental health support.

<b>
Entity diagram:
</b>

![image](https://github.com/Talida-M/AWBD_Project/assets/75331740/bbeab4f3-039a-444b-9e68-e1a2e04aecac)


<details> 
 <summary>
  <b>
 Relationships between entities

  </b>
 </summary>
 
![image](https://github.com/Talida-M/AWBD_Project/assets/75331740/d4cd10e3-dd72-4bb7-a6ae-c3e6f863799c)

![image](https://github.com/Talida-M/AWBD_Project/assets/75331740/a1f979f4-4089-418e-9bbd-e1f50960a764)

![image](https://github.com/Talida-M/AWBD_Project/assets/75331740/a40d8e80-4652-4e1f-96c6-7a4cc95de3a5)


</details>

<details> 
 <summary>
  <b>
 CRUD

  </b>
 </summary>
 
![image](https://github.com/Talida-M/AWBD_Project/assets/75331740/c138f2bc-22b8-4a5e-9f5d-1837caf1c7bb)

![image](https://github.com/Talida-M/AWBD_Project/assets/75331740/4965713d-faf6-47a6-9b38-7fb76fafb5ed)

![image](https://github.com/Talida-M/AWBD_Project/assets/75331740/4282675b-b6df-44db-9f01-c181c177e836)

</details>

<details> 
 <summary>
  <b>
Profile and Databases In Our Project

  </b>
 </summary>
To effectively manage the execution of our tests across different database environments—specifically MySQL and H2—you need a strategic approach in setting active profiles. Here’s a streamlined method to ensure that tests appropriate for each database are run accurately:

Strategy for Running Database-Specific Tests:
For MySQL:

Profile Configuration: Begin by setting the active profile to 'mysql' to prioritize MySQL configurations.
Execution: Proceed to run the suite of tests designated for MySQL. These tests are tailored to interact specifically with the MySQL database settings.

For H2:

Profile Adjustment: Switch the active profile to 'h2' to accommodate tests that are specifically designed for the H2 database.
Test Execution: Once the profile is set, execute the H2-specific tests.
By adopting this method, we ensure that each set of tests runs under conditions that match their database requirements, thereby avoiding conflicts and errors that arise from profile mismatches. This tailored approach not only enhances test accuracy but also maintains the integrity of our testing environment across different database platforms.

![image](https://github.com/Talida-M/AWBD_Project/assets/75331740/4bc4c22f-a0f2-4400-ba80-141881fcb2bf)


![image](https://github.com/Talida-M/AWBD_Project/assets/75331740/d479e9a4-545d-4f62-84e0-b8d3b226c90b)

The tests for the services are made with H2 Profile.
![image](https://github.com/Talida-M/AWBD_Project/assets/75331740/f116903e-d629-4773-a357-a91f6ab4cf63)


The tests for Controllers are mada with MySQL Profile.
![image](https://github.com/Talida-M/AWBD_Project/assets/75331740/d49d7e5c-359f-40dc-a210-cf632a6a64a3)

</details>

<details> 
 <summary>
  <b>
Unit Tests  for  REST endpoints and services

  </b>
 </summary>
 
![image](https://github.com/Talida-M/AWBD_Project/assets/75331740/2f90aa94-7b25-49d3-a5c5-d7c88942f93a)


</details>

<details> 
 <summary>
  <b>
Forms data validation
      
  </b>
 </summary>
 
- The Model:
  
The annotations like  @Min, @Positive, @Email will be validated when the object will be used in the same time with @Valid annotation in functions.

<img width="552" alt="Screenshot 2024-05-13 at 10 12 37" src="https://github.com/Talida-M/AWBD_Project/assets/141910803/2f0cee05-cae0-4978-9762-4117825c8754">

<img width="499" alt="Screenshot 2024-05-13 at 10 49 21" src="https://github.com/Talida-M/AWBD_Project/assets/141910803/22efe50c-668d-4872-9ec1-68a280d493c0">



- Controller:
  
We used BindingResult to detect errors in case the form was not filled out correctly
  
<img width="891" alt="Screenshot 2024-05-13 at 10 23 13" src="https://github.com/Talida-M/AWBD_Project/assets/141910803/142fce06-7507-4218-a4d9-eb53d096465c">

- Frontend:

  <img width="916" alt="Screenshot 2024-05-13 at 10 22 49" src="https://github.com/Talida-M/AWBD_Project/assets/141910803/2be76d07-e457-4e66-8209-8548f8787019">


</details>

<details> 
 <summary>
  <b>
 Exception custom pages
      
  </b>
 </summary>
 
![image](https://github.com/Talida-M/AWBD_Project/assets/75331740/61d31164-a4ad-4cb4-919b-bb7e1fde1b27)

![image](https://github.com/Talida-M/AWBD_Project/assets/75331740/72e85608-e413-40ba-9492-23dd0555e55f)


</details>

<details> 
 <summary>
  <b>
 Logging
      
  </b>
 </summary>
 
We used logs especially in tests file.
![image](https://github.com/Talida-M/AWBD_Project/assets/75331740/5d51dba4-0ef9-4c01-af8c-63767b733085)


</details>

<details> 
 <summary>
  <b>
Paginate and Sorting
      
  </b>
 </summary>
 
Repo:

![image](https://github.com/Talida-M/AWBD_Project/assets/75331740/37b6e02b-0382-435d-a550-a6a0ee2831c5)

Service:
![image](https://github.com/Talida-M/AWBD_Project/assets/75331740/3bf51994-1c45-41b6-89a0-c24583c1b797)

Controller:
![image](https://github.com/Talida-M/AWBD_Project/assets/75331740/4224c24a-88ea-43ca-be7d-72ae6e8e7656)



</details>

<details> 
 <summary>
  <b>
 Spring Security - JDBC Authentication
      
  </b>
 </summary>
Security, Role Permissions
 
![image](https://github.com/Talida-M/AWBD_Project/assets/75331740/2d6c1fc8-691f-45a0-87bf-a0cacfb0d388)
![image](https://github.com/Talida-M/AWBD_Project/assets/75331740/3e0aeaab-ecca-4e4f-a454-cea69f9a4172)



Login Form

![image](https://github.com/Talida-M/AWBD_Project/assets/75331740/371ddbea-d23c-4334-a2fd-91aba115aa8b)

</details>
