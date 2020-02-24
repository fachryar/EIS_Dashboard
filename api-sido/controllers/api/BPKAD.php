<?php
use Restserver\Libraries\REST_Controller;
defined('BASEPATH') OR exit('No direct script access allowed');

require APPPATH . 'libraries/REST_Controller.php';
require APPPATH . 'libraries/Format.php';

class BPKAD extends REST_Controller {

	public function __construct(){
		parent::__construct();
		$this->load->model('BPKAD_model');
	}

	//GET APBD BY TAHUN
	public function apbd_get(){

		$tahun = $this->get('tahun');
		$apbd = $this->BPKAD_model->getTotalAPBD($tahun);
		
		if ($apbd) {
			$this->response([
	            'status' => true,
	            'apbd' => $apbd
			], REST_Controller::HTTP_OK);
		} else {
			$this->response([
	            'status' => false,
	            'message' => 'data tidak ditemukan'
			], REST_Controller::HTTP_NOT_FOUND);
		}
	}

	// GET PENDAPATAN ATAU BELANJA U2
	public function anggaranrealisasi1_get(){
		$kategori = $this->get('kategori');
		$tahun = $this->get('tahun');
		$apbd = $this->BPKAD_model->getAnggaranRealisasi1($kategori, $tahun);
		
		if ($apbd) {
			$this->response([
	            'status' => true,
	            'apbd' => $apbd
			], REST_Controller::HTTP_OK);
		} else {
			$this->response([
	            'status' => false,
	            'message' => 'data tidak ditemukan'
			], REST_Controller::HTTP_NOT_FOUND);
		}
	}

	// GET PENDAPATAN ATAU BELANJA U3
	public function anggaranrealisasi2_get(){
		$kategori = $this->get('kategori');
		$tahun = $this->get('tahun');
		$apbd = $this->BPKAD_model->getAnggaranRealisasi2($kategori, $tahun);
		
		if ($apbd) {
			$this->response([
	            'status' => true,
	            'apbd' => $apbd
			], REST_Controller::HTTP_OK);
		} else {
			$this->response([
	            'status' => false,
	            'message' => 'data tidak ditemukan'
			], REST_Controller::HTTP_NOT_FOUND);
		}
	}

}