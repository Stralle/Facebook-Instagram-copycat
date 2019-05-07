import {CommentDTO} from './comment.response.DTO';
import {UserDTO} from './user.response.DTO';

export class PostDTO {
  id: number;
  title: string;
  description: string;
  imageUrl: string;
  createdAt: string;
  totalLikes: number;
  totalComments: number;
  user: UserDTO;
  comments: CommentDTO[];
  likes: string[];
}
