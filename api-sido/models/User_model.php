<?php

class User_model extends CI_Model{

	public function login_api($username, $password){
		return $this->db->query('SELECT username, nama_user, jabatan, skpd, no_hp FROM user.users WHERE USERNAME = '."'".$username."'".' AND PASSWORD = '."'".$password."'")->result_array();

	}

	public function getAllUser($username){
		return $this->db->query('SELECT * FROM user.users 
								WHERE JABATAN NOT LIKE '."'Admin'".'
									AND USERNAME NOT LIKE '."'".$username."'
									GROUP BY USERNAME
									ORDER BY JABATAN ASC;")->result_array();

	}


}