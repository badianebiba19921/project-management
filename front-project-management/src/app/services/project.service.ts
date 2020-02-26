import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProjectService {

  //private baseUrl: string = 'http://localhost:8080/api/v1/projects';
  private baseUrl: string = 'http://localhost:8080/front-app/projects';

  constructor(private http: HttpClient) { }

  getProject(projectId: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/${projectId}`);
  }

  createProject(project: Object): Observable<Object>{
    return this.http.post(`${this.baseUrl}`, project);
  }

  updateProject(id: number, value: any): Observable<Object>{
    return this.http.put(`${this.baseUrl}/${id}`, value);
  }

  deleteProject(id: number): Observable<any>{
    return this.http.delete(`${this.baseUrl}/${id}`,{responseType: 'text'});
  }

  getProjectsList(): Observable<any>{
    return this.http.get(`${this.baseUrl}`);
  }
}
