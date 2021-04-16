import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { SERVER_API_URL } from '../../app.constants';
import { ResponseWrapper } from '../index';
import { Observable } from 'rxjs/Observable';

@Injectable()
export class BpmService {

    private resourceUrl = SERVER_API_URL + 'api/bpm';

    constructor(private http: Http) { }

    sendSignal(signalName: string): Observable<Response> {
        return this.http.post(`${this.resourceUrl}/signals/`, signalName);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }
}
