<?php
use Restserver\Libraries\REST_Controller;
defined('BASEPATH') OR exit('No direct script access allowed');

require APPPATH . 'libraries/REST_Controller.php';
require APPPATH . 'libraries/Format.php';

class User extends REST_Controller {

	public function __construct(){
		parent::__construct();
		$this->load->model('User_model');
	}

	//LOGIN
	public function login_post(){
		$username = $this->input->post('username');
		$password = $this->input->post('password');
		$user = $this->User_model->login_api($username,$password);
		
		if ($user) {
			$this->response([
	            'status' => true,
	            'user' => $user
			], REST_Controller::HTTP_OK);
		} else {
			$this->response([
	            'status' => false,
	            'message' => 'User not found'
			], REST_Controller::HTTP_NOT_FOUND);
		}
	}

	//GET ALL USER
	public function alluser_get(){
		$username = $this->input->get('username');
		$user = $this->User_model->getAllUser($username);
		
		if ($user) {
			$this->response([
	            'status' => true,
	            'user' => $user
			], REST_Controller::HTTP_OK);
		} else {
			$this->response([
	            'status' => false,
	            'message' => 'User not found'
			], REST_Controller::HTTP_NOT_FOUND);
		}
	}
}
