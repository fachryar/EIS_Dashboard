<?php

class Kategori_model extends CI_Model{
	public function getQuery($query) {
		return $this->db->query($query)->result_array();
	}

	public function getCategory() {
		return $this->db->query('SELECT * FROM KATEGORI.KATEGORI;')->result_array();
	}



}