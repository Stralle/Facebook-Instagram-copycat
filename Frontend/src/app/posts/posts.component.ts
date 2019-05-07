import { Component, OnInit } from '@angular/core';
import {PostDTO} from '../_models/post.response.DTO';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';

@Component({
  selector: 'app-posts',
  templateUrl: './posts.component.html',
  styleUrls: ['./posts.component.css']
})
export class PostsComponent implements OnInit {
  followingUsersPosts: PostDTO[];

  readonly FIND_FOLLOWING_POSTS_URL = 'http://localhost:8080/post/findFollowingPosts';
  readonly LIKE_POST_URL = 'http://localhost:8080/post/addLikeToPost';
  readonly COMMENT_POST_URL = 'http://localhost:8080/post/addCommmentToPost';
  readonly SHARE_POST_URL = 'http://localhost:8080/post/sharePost';
  readonly PHOTOS_URL = 'http://127.0.0.1:8887/';
  comment: string;

  constructor(private http: HttpClient) {
    this.getFollowingUsersPosts();
  }

  ngOnInit() {
  }

  getFollowingUsersPosts() {
    console.log('GETTING FOLLOWING POSTS');
    this.http.get<PostDTO[]>(this.FIND_FOLLOWING_POSTS_URL).subscribe( (posts: any) => {
      this.followingUsersPosts = posts;

      if (this.followingUsersPosts) {
        for (let i = 0; i < this.followingUsersPosts.length; i++) {
          console.log('asdf' + this.followingUsersPosts[i].id);
          console.log('asdf' + this.followingUsersPosts[i].imageUrl);

        }
      }
    });
  }

  likePhoto(post: PostDTO) {
    const params = new HttpParams()
      .set('postId', String(post.id));
    console.log('Post id: ' + post.id);
    this.http.post<PostDTO>(this.LIKE_POST_URL, {}, {params: params}).subscribe((postDTO: PostDTO) => {
      if (postDTO) {
        post.totalLikes = postDTO.totalLikes;
      } else {
        alert('Post nije lajkovan. Doslo je do greske!');
      }
    });
  }

  getFullPhotoUrl(post: PostDTO) {
    const s =  this.PHOTOS_URL + post.imageUrl;
    console.log(s);
    return s;
  }

  sharePhoto(post: PostDTO) {
    const params = new HttpParams()
      .set('postId', String(post.id));
    this.http.post<PostDTO>(this.SHARE_POST_URL, {}, {params: params}).subscribe((postDTO: PostDTO) => {
      if (postDTO) {
        alert('Uspesno share-ovan post na vas profil.');
      } else {
        alert('Post nije lajkovan. Doslo je do greske!');
      }
    });
  }

  commentPhoto(post: PostDTO) {
    this.http.post<PostDTO>(this.COMMENT_POST_URL, {'id': post.id, 'content': this.comment}).subscribe((postDTO: PostDTO) => {
      console.log('Dobijen postDTO: ' + JSON.stringify(postDTO));
      if (postDTO) {
        post.totalComments = postDTO.totalComments;
        console.log('radi');
      } else {
        alert('Post nije lajkovan. Doslo je do greske!');
      }
    });
  }
}
