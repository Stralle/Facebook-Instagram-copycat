import {PostDTO} from './post.response.DTO';

export class UserDTO {
  id: number;
  username: string;
  email: string;
  profileImageUrl: string;
  validated: boolean;
  followers: UserDTO[];
  following: UserDTO[];
  posts: PostDTO[];
  sharedPosts: PostDTO[];
}
