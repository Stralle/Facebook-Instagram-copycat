import {UserDTO} from './user.response.DTO';

export class LoginDTO {
  user: UserDTO;
  token: string;
}
