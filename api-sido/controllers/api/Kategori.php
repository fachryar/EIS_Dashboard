<?php
use Restserver\Libraries\REST_Controller;
defined('BASEPATH') OR exit('No direct script access allowed');

require APPPATH . 'libraries/REST_Controller.php';
require APPPATH . 'libraries/Format.php';

class Kategori extends REST_Controller {

	public function __construct(){
		parent::__construct();
		$this->load->model('Kategori_model');
	}

	public function testquery_post() {

		$query = $this->post("query");
		$kategori = $this->Kategori_model->getQuery($query);
		
		if ($kategori) {
			$this->response([
	            'status' => true,
	            'kategori' => $kategori
			], REST_Controller::HTTP_OK);
		} else {
			$this->response([
	            'status' => false,
	            'message' => 'data tidak ditemukan'
			], REST_Controller::HTTP_NOT_FOUND);
		}
	}

	public function allcategory_get() {

		$kategori = $this->Kategori_model->getCategory();
		
		if ($kategori) {
			$this->response([
	            'status' => true,
	            'kategori' => $kategori
			], REST_Controller::HTTP_OK);
		} else {
			$this->response([
	            'status' => false,
	            'message' => 'data tidak ditemukan'
			], REST_Controller::HTTP_NOT_FOUND);
		}
	}

}