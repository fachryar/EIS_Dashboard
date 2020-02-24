<?php
use Restserver\Libraries\REST_Controller;
defined('BASEPATH') OR exit('No direct script access allowed');

require APPPATH . 'libraries/REST_Controller.php';
require APPPATH . 'libraries/Format.php';


class Pajak extends REST_Controller {

	public function __construct(){
		parent::__construct();
		$this->load->model('Pajak_model');
	}

//GET TOTAL DAN TARGET MASING-MASING PAJAK TAHUN X
	public function target_get() {

		$tahun = $this->get('tahun');
		if($tahun === null){
			$pajak = $this->Pajak_model->getTargetPajak();
		} else {
			$pajak = $this->Pajak_model->getTargetPajak($tahun);
		}

		if ($pajak) {
			$this->response([
	            'status' => true,
	            'pajak' => $pajak
			], REST_Controller::HTTP_OK);
		} else {
			$this->response([
	            'status' => false,
	            'message' => 'data tidak ditemukan'
			], REST_Controller::HTTP_NOT_FOUND);
		}
	}

//GET TOTAL SEMUA PENDAPATAN PAJAK TAHUN X
	public function pendapatanall_get(){
		$tahun = $this->get('tahun');
		$pajak = $this->Pajak_model->getPendapatanAll($tahun);

		if ($pajak) {
			$this->response([
	            'status' => true,
	            'pajak' => $pajak
			], REST_Controller::HTTP_OK);
		} else {
			$this->response([
	            'status' => false,
	            'message' => 'data tidak ditemukan'
			], REST_Controller::HTTP_NOT_FOUND);
		}
	}

//GET TOTAL PENDAPATAN PAJAK MASING-MASING TAHUN X

}

// class Pajak extends REST_Controller {

// 	public function __construct(){
// 		parent::__construct();
// 		$this->load->model('Pajak_model');
// 	}

// 	public function index_get() {

// 		$id = $this->get('id_jenis_pajak');
// 		if ($id === null){
// 			$pajak = $this->Pajak_model->getPajak();
// 		} else {
// 			$pajak = $this->Pajak_model->getPajak($id);
// 		}
		
// 		if ($pajak) {
// 			$this->response([
// 	            'status' => true,
// 	            'data' => $pajak
// 			], REST_Controller::HTTP_OK);
// 		} else {
// 			$this->response([
// 	            'status' => false,
// 	            'message' => 'data tidak ditemukan'
// 			], REST_Controller::HTTP_NOT_FOUND);
// 		}
// 	}
// }

// class Pajak extends REST_Controller {

// 	public function __construct(){
// 		parent::__construct();
// 		$this->load->model('Pajak_model');
// 	}

// 	public function index_get() {
// 		$pajak = $this->Pajak_model->getPajak();
		
// 		if ($pajak) {
// 			$this->response([
// 	            'status' => true,
// 	            'data' => $pajak
// 			], REST_Controller::HTTP_OK);
// 		}
// 	}
// }